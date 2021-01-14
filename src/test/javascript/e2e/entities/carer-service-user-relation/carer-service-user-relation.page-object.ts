import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CarerServiceUserRelationUpdatePage from './carer-service-user-relation-update.page-object';

const expect = chai.expect;
export class CarerServiceUserRelationDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.carerServiceUserRelation.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-carerServiceUserRelation'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CarerServiceUserRelationComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('carer-service-user-relation-heading'));
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
    await navBarPage.getEntityPage('carer-service-user-relation');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCarerServiceUserRelation() {
    await this.createButton.click();
    return new CarerServiceUserRelationUpdatePage();
  }

  async deleteCarerServiceUserRelation() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const carerServiceUserRelationDeleteDialog = new CarerServiceUserRelationDeleteDialog();
    await waitUntilDisplayed(carerServiceUserRelationDeleteDialog.deleteModal);
    expect(await carerServiceUserRelationDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.carerServiceUserRelation.delete.question/
    );
    await carerServiceUserRelationDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(carerServiceUserRelationDeleteDialog.deleteModal);

    expect(await isVisible(carerServiceUserRelationDeleteDialog.deleteModal)).to.be.false;
  }
}
