import { IItem } from 'app/entities/item/item.model';

export interface ITags {
  id?: string;
  tag?: string;
  language?: string;
  sentries?: IItem[] | null;
}

export class Tags implements ITags {
  constructor(public id?: string, public tag?: string, public language?: string, public sentries?: IItem[] | null) {}
}

export function getTagsIdentifier(tags: ITags): string | undefined {
  return tags.id;
}
