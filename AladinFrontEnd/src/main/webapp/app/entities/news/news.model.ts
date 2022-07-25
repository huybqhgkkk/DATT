export interface INews {
  id?: number;
  title?: string | null;
  content?: string | null;
  image1ContentType?: string | null;
  image1?: string | null;
  image2ContentType?: string | null;
  image2?: string | null;
  image3ContentType?: string | null;
  image3?: string | null;
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string | null,
    public content?: string | null,
    public image1ContentType?: string | null,
    public image1?: string | null,
    public image2ContentType?: string | null,
    public image2?: string | null,
    public image3ContentType?: string | null,
    public image3?: string | null
  ) {}
}

export function getNewsIdentifier(news: INews): number | undefined {
  return news.id;
}
