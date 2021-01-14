import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import AnswerUpdatePage from './answer-update.page-object';

const expect = chai.expect;
export class AnswerDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.answer.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-answer'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class AnswerComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('answer-heading'));
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
    await navBarPage.getEntityPage('answer');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateAnswer() {
    await this.createButton.click();
    return new AnswerUpdatePage();
  }

  async deleteAnswer() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const answerDeleteDialog = new AnswerDeleteDialog();
    await waitUntilDisplayed(answerDeleteDialog.deleteModal);
    expect(await answerDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.answer.delete.question/);
    await answerDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(answerDeleteDialog.deleteModal);

    expect(await isVisible(answerDeleteDialog.deleteModal)).to.be.false;
  }
}
