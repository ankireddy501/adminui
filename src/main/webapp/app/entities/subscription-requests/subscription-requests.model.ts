import { BaseEntity, User } from './../../shared';

export const enum SubscriptionStatus {
    'PENDING',
    'APPROVED',
    'ACTIVE',
    'RENEWAL_PENDING'
}

export class SubscriptionRequests implements BaseEntity {
    constructor(
        public id?: number,
        public status?: SubscriptionStatus,
        public requestedDate?: any,
        public approvalDate?: any,
        public subscriptionAmount?: number,
        public startDate?: any,
        public endDate?: any,
        public user?: User,
    ) {
    }
}
