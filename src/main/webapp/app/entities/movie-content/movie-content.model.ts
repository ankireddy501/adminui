import { BaseEntity } from './../../shared';

export const enum SubscriptionType {
    'PAID',
    'FREE',
    'PREMIUM',
    'PREMIER'
}

export class MovieContent implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public contentPath?: string,
        public subscriptionType?: SubscriptionType,
        public creationTime?: any,
        public updateDate?: any,
        public details?: BaseEntity,
        public moviePosters?: BaseEntity[],
    ) {
    }
}
