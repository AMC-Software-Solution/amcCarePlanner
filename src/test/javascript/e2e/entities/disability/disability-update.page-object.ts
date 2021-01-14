import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class DisabilityUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.disability.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  isDisabledInput: ElementFinder = element(by.css('input#disability-isDisabled'));
  noteInput: ElementFinder = element(by.css('input#disability-note'));
  createdDateInput: ElementFinder = element(by.css('input#disability-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#disability-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#disability-clientId'));
  hasExtraDataInput: ElementFinder = element(by.css('input#disability-hasExtraData'));
  disabilityTypeSelect: ElementFinder = element(by.css('select#disability-disabilityType'));
  employeeSelect: ElementFinder = element(by.css('select#disability-employee'));

  getPageTitle() {
    return this.pageTitle;
  }

  getIsDisabledInput() {
    return this.isDisabledInput;
  }
  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return this.noteInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return this.createdDateInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
  }
  async disabilityTypeSelectLastOption() {
    await this.disabilityTypeSelect.all(by.tagName('option')).last().click();
  }

  async disabilityTypeSelectOption(option) {
    await this.disabilityTypeSelect.sendKeys(option);
  }

  getDisabilityTypeSelect() {
    return this.disabilityTypeSelect;
  }

  async getDisabilityTypeSelectedOption() {
    return this.disabilityTypeSelect.element(by.css('option:checked')).getText();
  }

  async employeeSelectLastOption() {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect() {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return this.employeeSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    const selectedIsDisabled = await this.getIsDisabledInput().isSelected();
    if (selectedIsDisabled) {
      await this.getIsDisabledInput().click();
      expect(await this.getIsDisabledInput().isSelected()).to.be.false;
    } else {
      await this.getIsDisabledInput().click();
      expect(await this.getIsDisabledInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.disabilityTypeSelectLastOption();
    await this.employeeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
