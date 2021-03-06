import dayjs from 'dayjs/esm';

export interface IDetails {
  id?: string;
  loginName?: string;
  password?: string;
  lastName?: string | null;
  firstName?: string | null;
  securtiyQuestion?: string | null;
  securityAnswer?: string | null;
  parentalPassword?: string | null;
  firstCdKey?: string | null;
  otherInformation?: string | null;
  enteredDate?: dayjs.Dayjs;
  orderDate?: dayjs.Dayjs | null;
}

export class Details implements IDetails {
  constructor(
    public id?: string,
    public loginName?: string,
    public password?: string,
    public lastName?: string | null,
    public firstName?: string | null,
    public securtiyQuestion?: string | null,
    public securityAnswer?: string | null,
    public parentalPassword?: string | null,
    public firstCdKey?: string | null,
    public otherInformation?: string | null,
    public enteredDate?: dayjs.Dayjs,
    public orderDate?: dayjs.Dayjs | null
  ) {}
}

export function getDetailsIdentifier(details: IDetails): string | undefined {
  return details.id;
}
