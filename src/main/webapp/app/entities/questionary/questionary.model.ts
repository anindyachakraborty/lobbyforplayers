export interface IQuestionary {
  id?: string;
  process?: string;
  questions?: string;
}

export class Questionary implements IQuestionary {
  constructor(public id?: string, public process?: string, public questions?: string) {}
}

export function getQuestionaryIdentifier(questionary: IQuestionary): string | undefined {
  return questionary.id;
}
