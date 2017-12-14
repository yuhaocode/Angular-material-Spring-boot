import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import 'hammerjs';


import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import {MatButtonModule, MatCheckboxModule} from '@angular/material';
import { NavBarComponent } from './componets/nav-bar/nav-bar.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { LoginComponent } from './componets/login/login.component';
import {MatListModule} from '@angular/material/list';

import {HttpModule} from '@angular/http';
import { routing } from './app.routing';
import { MatFormFieldModule } from '@angular/material';
import { MatGridListModule } from '@angular/material';
import {MatInputModule} from '@angular/material';
import { AddNewBookComponent } from './componets/add-new-book/add-new-book.component';
import {MatSelectModule} from '@angular/material/select';
import { MatDialogModule } from '@angular/material';

import {RemoveBookService} from './services/remove-book.service';
import { EditBookService } from './services/edit-book.service';
import { AddBookService } from './services/add-book.service';
import {LoginService} from './services/login.service';
import {UploadImageService} from './services/upload-image.service';
import { BookListComponent, DialogResultExampleDialog } from './componets/book-list/book-list.component';
import { GetBookListService} from './services/get-book-list.service';
import { GetBookService} from './services/get-book.service';
import { ViewBookComponent } from './componets/view-book/view-book.component';
import { EditBookComponent } from './componets/edit-book/edit-book.component';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    AddNewBookComponent,
    BookListComponent,
    ViewBookComponent,
    EditBookComponent,
    DialogResultExampleDialog
  ],
  imports: [
    MatSlideToggleModule,
    MatSelectModule,
    MatGridListModule,
    FormsModule,
    MatFormFieldModule,
    BrowserModule,
    NoopAnimationsModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    MatToolbarModule,
    HttpModule,
    routing,
    MatListModule,
    MatDialogModule
  ],
  providers: [
  	LoginService,
    AddBookService,
    UploadImageService,
    GetBookListService,
    GetBookService,
    EditBookService,
    RemoveBookService
  ],
  entryComponents: [DialogResultExampleDialog],
  bootstrap: [AppComponent]
})
export class AppModule { }
