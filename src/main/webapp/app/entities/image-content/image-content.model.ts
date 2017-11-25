import { BaseEntity } from './../../shared';

export const enum SubscriptionType {
    'PAID',
    'FREE',
    'PREMIUM'
}

export class ImageContent implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public caption?: boolean,
        public contentPath?: string,
        public subscriptionType?: SubscriptionType,
        public creationDate?: any,
        public updateDate?: any,
    ) {
        this.caption = false;
    }
}
