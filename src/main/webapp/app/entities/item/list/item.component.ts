import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IItem } from '../item.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ItemService } from '../service/item.service';
import { ItemDeleteDialogComponent } from '../delete/item-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss'],
})
export class ItemComponent implements OnInit {
  items: IItem[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;
  toggleFilter = true;
  fullFilters = true;
  someRange2config: any = {
    behaviour: 'drag',
    connect: true,
    margin: 10,
    start: [0, 100],
    range: {
      min: 0,
      max: 100,
    },
    tooltips: true,
  };
  filterText: Array<string> = [
    'Price: 20 to 80',
    'Filter Text',
    'Another One',
    'Woooo hoooo',
    'Price: 20 to 80',
    'Filter Text',
    'Another One',
    'Woooo hoooo',
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
  // gamesFilter: string;

  constructor(protected itemService: ItemService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.items = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;
    this.searchGames = this.games;
    this.itemService
      .query({
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

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
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
