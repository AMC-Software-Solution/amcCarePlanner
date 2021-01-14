import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServiceUserUpdatePage from './service-user-update.page-object';

const expect = chai.expect;
export class ServiceUserDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.serviceUser.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-serviceUser'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServiceUserComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('service-user-heading'));
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
    await navBarPage.getEntityPage('service-user');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServiceUser() {
    await this.createButton.click();
    return new ServiceUserUpdatePage();
  }

  async deleteServiceUser() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const serviceUserDeleteDialog = new ServiceUserDeleteDialog();
    await waitUntilDisplayed(serviceUserDeleteDialog.deleteModal);
    expect(await serviceUserDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.serviceUser.delete.question/);
    await serviceUserDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(serviceUserDeleteDialog.deleteModal);

    expect(await isVisible(serviceUserDeleteDialog.deleteModal)).to.be.false;
  }
}
