import { IItem } from 'app/entities/item/item.model';

export interface ITags {
  id?: number;
  tag?: string;
  language?: string;
  sentries?: IItem[] | null;
}

export class Tags implements ITags {
  constructor(public id?: number, public tag?: string, public language?: string, public sentries?: IItem[] | null) {}
}

export function getTagsIdentifier(tags: ITags): number | undefined {
  return tags.id;
}
