import * as dayjs from 'dayjs';

export interface IChats {
  id?: number;
  fromUserId?: string;
  toUserId?: string;
  timeStamp?: dayjs.Dayjs;
  message?: string;
  language?: string;
}

export class Chats implements IChats {
  constructor(
    public id?: number,
    public fromUserId?: string,
    public toUserId?: string,
    public timeStamp?: dayjs.Dayjs,
    public message?: string,
    public language?: string
  ) {}
}

export function getChatsIdentifier(chats: IChats): number | undefined {
  return chats.id;
}
