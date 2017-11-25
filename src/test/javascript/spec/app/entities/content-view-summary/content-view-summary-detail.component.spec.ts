/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AdminuiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContentViewSummaryDetailComponent } from '../../../../../../main/webapp/app/entities/content-view-summary/content-view-summary-detail.component';
import { ContentViewSummaryService } from '../../../../../../main/webapp/app/entities/content-view-summary/content-view-summary.service';
import { ContentViewSummary } from '../../../../../../main/webapp/app/entities/content-view-summary/content-view-summary.model';

describe('Component Tests', () => {

    describe('ContentViewSummary Management Detail Component', () => {
        let comp: ContentViewSummaryDetailComponent;
        let fixture: ComponentFixture<ContentViewSummaryDetailComponent>;
        let service: ContentViewSummaryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminuiTestModule],
                declarations: [ContentViewSummaryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContentViewSummaryService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContentViewSummaryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContentViewSummaryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentViewSummaryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContentViewSummary(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contentViewSummary).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
