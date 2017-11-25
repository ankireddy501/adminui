import { BaseEntity } from './../../shared';

export const enum ContentType {
    'IMAGE',
    'VIDEO'
}

export class ContentViewSummary implements BaseEntity {
    constructor(
        public id?: number,
        public viewDate?: any,
        public contentType?: ContentType,
        public contentId?: string,
    ) {
    }
}
