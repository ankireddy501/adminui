/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AdminuiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MoviePosterDetailComponent } from '../../../../../../main/webapp/app/entities/movie-poster/movie-poster-detail.component';
import { MoviePosterService } from '../../../../../../main/webapp/app/entities/movie-poster/movie-poster.service';
import { MoviePoster } from '../../../../../../main/webapp/app/entities/movie-poster/movie-poster.model';

describe('Component Tests', () => {

    describe('MoviePoster Management Detail Component', () => {
        let comp: MoviePosterDetailComponent;
        let fixture: ComponentFixture<MoviePosterDetailComponent>;
        let service: MoviePosterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminuiTestModule],
                declarations: [MoviePosterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MoviePosterService,
                    JhiEventManager
                ]
            }).overrideTemplate(MoviePosterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoviePosterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoviePosterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MoviePoster(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.moviePoster).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
