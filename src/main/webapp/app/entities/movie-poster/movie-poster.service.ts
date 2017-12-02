import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MoviePoster } from './movie-poster.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MoviePosterService {

    private resourceUrl = SERVER_API_URL + 'api/movie-posters';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(moviePoster: MoviePoster): Observable<MoviePoster> {
        const copy = this.convert(moviePoster);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(moviePoster: MoviePoster): Observable<MoviePoster> {
        const copy = this.convert(moviePoster);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MoviePoster> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to MoviePoster.
     */
    private convertItemFromServer(json: any): MoviePoster {
        const entity: MoviePoster = Object.assign(new MoviePoster(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        entity.updateDate = this.dateUtils
            .convertLocalDateFromServer(json.updateDate);
        return entity;
    }

    /**
     * Convert a MoviePoster to a JSON which can be sent to the server.
     */
    private convert(moviePoster: MoviePoster): MoviePoster {
        const copy: MoviePoster = Object.assign({}, moviePoster);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(moviePoster.creationDate);
        copy.updateDate = this.dateUtils
            .convertLocalDateToServer(moviePoster.updateDate);
        return copy;
    }
}
