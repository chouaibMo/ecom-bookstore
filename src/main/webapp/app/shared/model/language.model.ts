export interface ILanguage {
  id?: number;
  language?: string;
}

export class Language implements ILanguage {
  constructor(public id?: number, public language?: string) {}
}
