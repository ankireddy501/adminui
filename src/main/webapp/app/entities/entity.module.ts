import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AdminuiImageContentModule } from './image-content/image-content.module';
import { AdminuiMovieContentModule } from './movie-content/movie-content.module';
import { AdminuiMovieContentDetailsModule } from './movie-content-details/movie-content-details.module';
import { AdminuiSubscriptionRequestsModule } from './subscription-requests/subscription-requests.module';
import { AdminuiContentViewSummaryModule } from './content-view-summary/content-view-summary.module';
import { AdminuiMoviePosterModule } from './movie-poster/movie-poster.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AdminuiImageContentModule,
        AdminuiMovieContentModule,
        AdminuiMovieContentDetailsModule,
        AdminuiSubscriptionRequestsModule,
        AdminuiContentViewSummaryModule,
        AdminuiMoviePosterModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminuiEntityModule {}
