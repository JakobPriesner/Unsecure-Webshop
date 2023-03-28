import {Commentary} from "./commentary";

export interface Article {
  articleNumber: number;
  modelName: string;
  amount: number;
  brand?: string;
  stars?: number;
  operatingSystem?: string;
  releaseDate?: string;
  screen?: string;
  resolution?: string;
  pictureIds?: number[];
  comments?: Commentary[];
}
