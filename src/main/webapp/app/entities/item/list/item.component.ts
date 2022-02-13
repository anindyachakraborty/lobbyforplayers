import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IItem } from '../item.model';

import { ASC, DESC } from 'app/config/pagination.constants';
import { ItemService } from '../service/item.service';
import { ItemDeleteDialogComponent } from '../delete/item-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss'],
})
export class ItemComponent implements OnInit {
  ITEM_THIS_PAGE = 9;
  TOTAL_ITEMS = 0;
  items: IItem[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;
  toggleFilter = true;
  fullFilters = true;
  @ViewChild('priceSlider') priceSlider: any | undefined;
  originalPrices: { min: number; max: number } = {
    min: 0,
    max: 100,
  };
  priceRange: any = {
    behaviour: 'drag',
    connect: true,
    margin: 10,
    start: [this.originalPrices.min, this.originalPrices.max],
    range: {
      min: this.originalPrices.min,
      max: this.originalPrices.max,
    },
    tooltips: true,
  };
  prices: { min: number; max: number } = {
    min: 0,
    max: 100,
  };
  filterText: Array<string> = [
    'Game: PUBG',
    'Game: BGMI',
    // 'Another One',
    // 'Woooo hoooo',
    // 'Price: 20 to 80',
    // 'Filter Text',
    // 'Another One',
    // 'Woooo hoooo',
  ];
  games = [
    'PUBG',
    'BGMI',
    'Counter Strike',
    'Call of Duty',
    'Fifa',
    'Cricket',
    'Snake',
    'GTA Vice City',
    'Dota 2',
    'Fortnite',
    'PUBG Mobile',
    'Fortnite Mobile',
  ];
  searchGames: string[] = [];
  gamesFilter: string[] = ['BGMI', 'PUBG'];

  constructor(protected itemService: ItemService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.items = [];
    this.itemsPerPage = this.ITEM_THIS_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'createdDate';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;
    this.searchGames = this.games;
    const minSync = new Promise((resolve, reject) => {
      this.itemService
        .minPrice({
          games: this.gamesFilter,
        })
        .subscribe(
          (res: HttpResponse<number>) => {
            this.isLoading = false;
            res.body ? (this.originalPrices.min = res.body) : (this.originalPrices.min = 0);
            this.priceSlider.slider.updateOptions({
              start: [this.originalPrices.min, this.originalPrices.max],
              range: { min: this.originalPrices.min, max: this.originalPrices.max },
            });
            this.prices.min = this.originalPrices.min;
            resolve(true);
          },
          () => {
            this.isLoading = false;
            reject(false);
          }
        );
    });
    const maxSync = new Promise((resolve, reject) => {
      this.itemService
        .maxPrice({
          games: this.gamesFilter,
        })
        .subscribe(
          (res: HttpResponse<number>) => {
            this.isLoading = false;
            res.body ? (this.originalPrices.max = res.body) : (this.originalPrices.max = 100);
            this.priceSlider.slider.updateOptions({
              start: [this.originalPrices.min, this.originalPrices.max],
              range: { min: this.originalPrices.min, max: this.originalPrices.max },
            });
            this.prices.max = this.originalPrices.max;
            resolve(true);
          },
          () => {
            this.isLoading = false;
            reject(false);
          }
        );
    });
    Promise.allSettled([minSync, maxSync])
      .then(() => {
        this.loadFilteredCards();
      })
      .catch(reason => {
        console.error(reason);
      });

    // this.itemService
    //   .query({
    //     page: this.page,
    //     size: this.itemsPerPage,
    //     sort: this.sort(),
    //   })
    //   .subscribe(
    //     (res: HttpResponse<IItem[]>) => {
    //       this.isLoading = false;
    //       this.paginateItems(res.body, res.headers);
    //     },
    //     () => {
    //       this.isLoading = false;
    //     }
    //   );
  }
  loadFilteredCards(): void {
    this.itemService
      .filterTotalCount({
        games: this.gamesFilter,
        min: this.prices.min,
        max: this.prices.max,
      })
      .subscribe(
        (res: HttpResponse<number>) => {
          this.isLoading = false;
          res.body != null ? (this.TOTAL_ITEMS = res.body) : (this.isLoading = false);
        },
        () => {
          this.isLoading = false;
        }
      );
    this.itemService
      .filtered({
        games: this.gamesFilter,
        min: this.prices.min,
        max: this.prices.max,
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IItem[]>) => {
          this.isLoading = false;
          this.paginateItems(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  createSlider(value: any): void {
    console.error(value);
  }

  updatePrice(value: any): void {
    this.prices.min = value[0];
    this.prices.max = value[1];
  }

  reset(): void {
    this.page = 0;
    this.items = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IItem): string {
    return item.id!;
  }

  removeFilter(index: number): void {
    this.filterText.splice(index, 1);
  }

  delete(item: IItem): void {
    const modalRef = this.modalService.open(ItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.item = item;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  toggleFilterForSmallScreen(): void {
    this.toggleFilter = !this.toggleFilter;
  }

  toggleFullFilters(): void {
    this.fullFilters = !this.fullFilters;
  }
  onChangeGameName(value: any): void {
    this.searchGames = this.games.filter(game => game.toLowerCase().indexOf(value.target.value.toLowerCase()) !== -1);
  }

  minPriceChanged(): void {
    if (this.prices.min < this.originalPrices.min) {
      this.prices.min = this.originalPrices.min;
    }
    this.priceSlider.slider.set([this.prices.min, null]);
  }

  maxPriceChanged(): void {
    if (this.prices.max > this.originalPrices.max) {
      this.prices.max = this.originalPrices.max;
    }
    this.priceSlider.slider.set([null, this.prices.max]);
  }

  onSelectGameName(game: string, event: any): void {
    if (event.target.checked) {
      this.gamesFilter.push(game);
    } else {
      this.gamesFilter.splice(this.gamesFilter.indexOf(game), 1);
    }
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    return result;
  }

  protected paginateItems(data: IItem[] | null, headers: HttpHeaders): void {
    const linkHeader = headers.get('link');
    if (linkHeader) {
      this.links = this.parseLinks.parse(linkHeader);
    } else {
      this.links = {
        last: 0,
      };
    }
    if (data) {
      for (const d of data) {
        this.items.push(d);
      }
    }
  }
}
