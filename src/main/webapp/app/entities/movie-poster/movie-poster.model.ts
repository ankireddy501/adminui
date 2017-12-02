import { BaseEntity } from './../../shared';

export const enum ImageType {
    'THUMBNAIL',
    'RAW'
}

export class MoviePoster implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public caption?: boolean,
        public contentContentType?: string,
        public content?: any,
        public creationDate?: any,
        public updateDate?: any,
        public posterSize?: ImageType,
        public movieContent?: BaseEntity,
    ) {
        this.caption = false;
    }
}
