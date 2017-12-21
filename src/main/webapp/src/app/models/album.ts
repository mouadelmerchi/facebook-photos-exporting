export class Album {
    id: string;
    name: string;
    count: number;
    coverPhotoUri: string;
    type: string;
}

export class AlbumsListResponse {
    albums: Array<Album>;
    currentPage: number;
    totalCount: number;
    pageSize: number;
    pagesToShow: number;
}