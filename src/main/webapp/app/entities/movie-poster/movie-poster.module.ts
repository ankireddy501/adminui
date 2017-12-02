import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminuiSharedModule } from '../../shared';
import {
    MoviePosterService,
    MoviePosterPopupService,
    MoviePosterComponent,
    MoviePosterDetailComponent,
    MoviePosterDialogComponent,
    MoviePosterPopupComponent,
    MoviePosterDeletePopupComponent,
    MoviePosterDeleteDialogComponent,
    moviePosterRoute,
    moviePosterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...moviePosterRoute,
    ...moviePosterPopupRoute,
];

@NgModule({
    imports: [
        AdminuiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MoviePosterComponent,
        MoviePosterDetailComponent,
        MoviePosterDialogComponent,
        MoviePosterDeleteDialogComponent,
        MoviePosterPopupComponent,
        MoviePosterDeletePopupComponent,
    ],
    entryComponents: [
        MoviePosterComponent,
        MoviePosterDialogComponent,
        MoviePosterPopupComponent,
        MoviePosterDeleteDialogComponent,
        MoviePosterDeletePopupComponent,
    ],
    providers: [
        MoviePosterService,
        MoviePosterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminuiMoviePosterModule {}
