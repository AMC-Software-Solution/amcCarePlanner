import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ExtraDataUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.extraData.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  entityNameInput: ElementFinder = element(by.css('input#extra-data-entityName'));
  entityIdInput: ElementFinder = element(by.css('input#extra-data-entityId'));
  extraDataKeyInput: ElementFinder = element(by.css('input#extra-data-extraDataKey'));
  extraDataValueInput: ElementFinder = element(by.css('input#extra-data-extraDataValue'));
  extraDataValueDataTypeSelect: ElementFinder = element(by.css('select#extra-data-extraDataValueDataType'));
  extraDataDescriptionInput: ElementFinder = element(by.css('input#extra-data-extraDataDescription'));
  extraDataDateInput: ElementFinder = element(by.css('input#extra-data-extraDataDate'));
  hasExtraDataInput: ElementFinder = element(by.css('input#extra-data-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setEntityNameInput(entityName) {
    await this.entityNameInput.sendKeys(entityName);
  }

  async getEntityNameInput() {
    return this.entityNameInput.getAttribute('value');
  }

  async setEntityIdInput(entityId) {
    await this.entityIdInput.sendKeys(entityId);
  }

  async getEntityIdInput() {
    return this.entityIdInput.getAttribute('value');
  }

  async setExtraDataKeyInput(extraDataKey) {
    await this.extraDataKeyInput.sendKeys(extraDataKey);
  }

  async getExtraDataKeyInput() {
    return this.extraDataKeyInput.getAttribute('value');
  }

  async setExtraDataValueInput(extraDataValue) {
    await this.extraDataValueInput.sendKeys(extraDataValue);
  }

  async getExtraDataValueInput() {
    return this.extraDataValueInput.getAttribute('value');
  }

  async setExtraDataValueDataTypeSelect(extraDataValueDataType) {
    await this.extraDataValueDataTypeSelect.sendKeys(extraDataValueDataType);
  }

  async getExtraDataValueDataTypeSelect() {
    return this.extraDataValueDataTypeSelect.element(by.css('option:checked')).getText();
  }

  async extraDataValueDataTypeSelectLastOption() {
    await this.extraDataValueDataTypeSelect.all(by.tagName('option')).last().click();
  }
  async setExtraDataDescriptionInput(extraDataDescription) {
    await this.extraDataDescriptionInput.sendKeys(extraDataDescription);
  }

  async getExtraDataDescriptionInput() {
    return this.extraDataDescriptionInput.getAttribute('value');
  }

  async setExtraDataDateInput(extraDataDate) {
    await this.extraDataDateInput.sendKeys(extraDataDate);
  }

  async getExtraDataDateInput() {
    return this.extraDataDateInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
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
    await this.setEntityNameInput('entityName');
    expect(await this.getEntityNameInput()).to.match(/entityName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEntityIdInput('5');
    expect(await this.getEntityIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setExtraDataKeyInput('extraDataKey');
    expect(await this.getExtraDataKeyInput()).to.match(/extraDataKey/);
    await waitUntilDisplayed(this.saveButton);
    await this.setExtraDataValueInput('extraDataValue');
    expect(await this.getExtraDataValueInput()).to.match(/extraDataValue/);
    await waitUntilDisplayed(this.saveButton);
    await this.extraDataValueDataTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setExtraDataDescriptionInput('extraDataDescription');
    expect(await this.getExtraDataDescriptionInput()).to.match(/extraDataDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.setExtraDataDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getExtraDataDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
