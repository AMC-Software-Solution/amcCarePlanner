import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServiceUserLocationUpdatePage from './service-user-location-update.page-object';

const expect = chai.expect;
export class ServiceUserLocationDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.serviceUserLocation.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-serviceUserLocation'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServiceUserLocationComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('service-user-location-heading'));
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
    await navBarPage.getEntityPage('service-user-location');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServiceUserLocation() {
    await this.createButton.click();
    return new ServiceUserLocationUpdatePage();
  }

  async deleteServiceUserLocation() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const serviceUserLocationDeleteDialog = new ServiceUserLocationDeleteDialog();
    await waitUntilDisplayed(serviceUserLocationDeleteDialog.deleteModal);
    expect(await serviceUserLocationDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.serviceUserLocation.delete.question/
    );
    await serviceUserLocationDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(serviceUserLocationDeleteDialog.deleteModal);

    expect(await isVisible(serviceUserLocationDeleteDialog.deleteModal)).to.be.false;
  }
}
