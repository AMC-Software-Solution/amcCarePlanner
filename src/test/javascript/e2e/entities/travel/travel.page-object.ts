import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import TravelUpdatePage from './travel-update.page-object';

const expect = chai.expect;
export class TravelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.travel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-travel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class TravelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('travel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('travel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateTravel() {
    await this.createButton.click();
    return new TravelUpdatePage();
  }

  async deleteTravel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const travelDeleteDialog = new TravelDeleteDialog();
    await waitUntilDisplayed(travelDeleteDialog.deleteModal);
    expect(await travelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.travel.delete.question/);
    await travelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(travelDeleteDialog.deleteModal);

    expect(await isVisible(travelDeleteDialog.deleteModal)).to.be.false;
  }
}
