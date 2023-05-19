import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AdminPanelService} from "../services/admin-panel.service";

@Component({
  selector: 'admin-panel',
  templateUrl: './admin-panel.component.html'
})

export class AdminPanelComponent implements OnInit {
  items !: any;
  pageNum: number = 1;
  pageSize: number = 12;
  totalElems!: number;

  constructor(private router: Router,
              private service: AdminPanelService) {
  }

  ngOnInit() {
    this.service.getItemsSubject();
    this.service.products.subscribe(items => {
      this.items = items.content
      this.totalElems = items.totalElements
    })
    this.onPageChange(this.pageNum)
  }

  public onPageChange(pageNum: number): void {
    this.service.getItemsPaginations(pageNum, this.pageSize)
  }
}
