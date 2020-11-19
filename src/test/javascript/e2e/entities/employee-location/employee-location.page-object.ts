import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EmployeeLocationUpdatePage from './employee-location-update.page-object';

const expect = chai.expect;
export class EmployeeLocationDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.employeeLocation.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeeLocation'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EmployeeLocationComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('employee-location-heading'));
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
    await navBarPage.getEntityPage('employee-location');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEmployeeLocation() {
    await this.createButton.click();
    return new EmployeeLocationUpdatePage();
  }

  async deleteEmployeeLocation() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const employeeLocationDeleteDialog = new EmployeeLocationDeleteDialog();
    await waitUntilDisplayed(employeeLocationDeleteDialog.deleteModal);
    expect(await employeeLocationDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.employeeLocation.delete.question/
    );
    await employeeLocationDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(employeeLocationDeleteDialog.deleteModal);

    expect(await isVisible(employeeLocationDeleteDialog.deleteModal)).to.be.false;
  }
}
