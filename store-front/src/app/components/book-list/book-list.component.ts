import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';
import {Params, ActivatedRoute, Router} from '@angular/router';
import {Http} from '@angular/http';
import {AppConst} from '../../constants/app-const';
import { Subject } from 'rxjs/Subject';

@Component({
	selector: 'app-book-list',
	templateUrl: './book-list.component.html',
	styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

	public filterQuery = "";
	public rowsOnPage = 5;
	private bookCache : Book[] = [];
	public dtTrigger: Subject<any> = new Subject();
	public dtOptions: DataTables.Settings = {};
	private selectedBook: Book;
	public bookList: Book[];
	private serverPath = AppConst.serverPath;

	constructor(
		private bookService:BookService,
		private router:Router,
		private http:Http,
		private route:ActivatedRoute
		) { }

	onSelect(book: Book) {
		this.selectedBook = book;
		this.bookCache.push(book);
		this.router.navigate(['/bookDetail', this.selectedBook.id]);
	}

	ngOnInit() : void {
		    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10
    };
    	this.bookService.getBookList().subscribe(
    		res => {
    			console.log(res.json());
    			this.bookList = res.json();
    			this.dtTrigger.next();
    		},
    		err => {
    			console.log(err);
    		}
    		);
		
	}
}


