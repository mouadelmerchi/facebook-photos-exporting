export class Photo {
    id: string;
    name: string;
    photoUri: string;
    imageKey: string;
}

export class PhotosListResponse {
    photos: Array<Photo>;
    albumName: string;
    currentPage: number;
    totalCount: number;
    pageSize: number;
    pagesToShow: number;
}