import { BaseEntity } from './../../shared';

export const enum Generes {
    'ABSURDIST',
    'ACTION',
    'ADVENTURE',
    'COMEDY',
    'CRIME',
    'DRAMA',
    'FANTASY',
    'HISTORICAL',
    'HORROR',
    'MYSTERY',
    'PARANOID',
    'PHILOSOPHICAL',
    'POLITICAL',
    'ROMANCE',
    'SAGA',
    'SATIRE',
    'SCIENCE_FICTION',
    'SOCIAL',
    'SPECULATIVE',
    'THRILLER',
    'URBAN',
    'WESTERN',
    'SLICE_OF_LIFE'
}

export const enum Language {
    'ARABIC',
    'BENGALI',
    'BULGARIAN',
    'CHINESE',
    'CROATIAN',
    'DUTCH',
    'ENGLISH',
    'FINNISH',
    'FRENCH',
    'GERMAN',
    'GREEK',
    'HEBREW',
    'HINDI',
    'HUNGARIAN',
    'ICELANDIC',
    'ITALIAN',
    'JAPANESE',
    'KANNADA',
    'KOREAN',
    'MALAYALAM',
    'MARATHI',
    'NORWEGIAN',
    'PERSIAN',
    'POLISH',
    'PORTUGUESE',
    'PUNJABI',
    'ROMANIAN',
    'RUSSIAN',
    'SPANISH',
    'SWEDISH',
    'TAMIL',
    'TELUGU',
    'TURKISH',
    'UKRAINIAN'
}

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
        public generes?: Generes,
        public language?: Language,
        public subscriptionType?: SubscriptionType,
        public creationTime?: any,
        public updateDate?: any,
        public details?: BaseEntity,
        public moviePosters?: BaseEntity[],
    ) {
    }
}
