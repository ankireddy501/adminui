/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AdminuiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SubscriptionRequestsDetailComponent } from '../../../../../../main/webapp/app/entities/subscription-requests/subscription-requests-detail.component';
import { SubscriptionRequestsService } from '../../../../../../main/webapp/app/entities/subscription-requests/subscription-requests.service';
import { SubscriptionRequests } from '../../../../../../main/webapp/app/entities/subscription-requests/subscription-requests.model';

describe('Component Tests', () => {

    describe('SubscriptionRequests Management Detail Component', () => {
        let comp: SubscriptionRequestsDetailComponent;
        let fixture: ComponentFixture<SubscriptionRequestsDetailComponent>;
        let service: SubscriptionRequestsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminuiTestModule],
                declarations: [SubscriptionRequestsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SubscriptionRequestsService,
                    JhiEventManager
                ]
            }).overrideTemplate(SubscriptionRequestsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubscriptionRequestsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubscriptionRequestsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SubscriptionRequests(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.subscriptionRequests).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
