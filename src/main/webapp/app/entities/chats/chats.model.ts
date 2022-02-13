import dayjs from 'dayjs/esm';

export interface IChats {
  id?: string;
  fromUserId?: string;
  toUserId?: string;
  timeStamp?: dayjs.Dayjs;
  message?: string;
  language?: string;
}

export class Chats implements IChats {
  constructor(
    public id?: string,
    public fromUserId?: string,
    public toUserId?: string,
    public timeStamp?: dayjs.Dayjs,
    public message?: string,
    public language?: string
  ) {}
}

export function getChatsIdentifier(chats: IChats): string | undefined {
  return chats.id;
}
