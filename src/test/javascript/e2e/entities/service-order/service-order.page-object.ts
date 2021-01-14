import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServiceOrderUpdatePage from './service-order-update.page-object';

const expect = chai.expect;
export class ServiceOrderDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.serviceOrder.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-serviceOrder'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServiceOrderComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('service-order-heading'));
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
    await navBarPage.getEntityPage('service-order');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServiceOrder() {
    await this.createButton.click();
    return new ServiceOrderUpdatePage();
  }

  async deleteServiceOrder() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const serviceOrderDeleteDialog = new ServiceOrderDeleteDialog();
    await waitUntilDisplayed(serviceOrderDeleteDialog.deleteModal);
    expect(await serviceOrderDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.serviceOrder.delete.question/);
    await serviceOrderDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(serviceOrderDeleteDialog.deleteModal);

    expect(await isVisible(serviceOrderDeleteDialog.deleteModal)).to.be.false;
  }
}
