import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class BranchUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.branch.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#branch-name'));
  addressInput: ElementFinder = element(by.css('input#branch-address'));
  telephoneInput: ElementFinder = element(by.css('input#branch-telephone'));
  contactNameInput: ElementFinder = element(by.css('input#branch-contactName'));
  branchEmailInput: ElementFinder = element(by.css('input#branch-branchEmail'));
  photoInput: ElementFinder = element(by.css('input#file_photo'));
  photoUrlInput: ElementFinder = element(by.css('input#branch-photoUrl'));
  createdDateInput: ElementFinder = element(by.css('input#branch-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#branch-lastUpdatedDate'));
  hasExtraDataInput: ElementFinder = element(by.css('input#branch-hasExtraData'));
  clientSelect: ElementFinder = element(by.css('select#branch-client'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setTelephoneInput(telephone) {
    await this.telephoneInput.sendKeys(telephone);
  }

  async getTelephoneInput() {
    return this.telephoneInput.getAttribute('value');
  }

  async setContactNameInput(contactName) {
    await this.contactNameInput.sendKeys(contactName);
  }

  async getContactNameInput() {
    return this.contactNameInput.getAttribute('value');
  }

  async setBranchEmailInput(branchEmail) {
    await this.branchEmailInput.sendKeys(branchEmail);
  }

  async getBranchEmailInput() {
    return this.branchEmailInput.getAttribute('value');
  }

  async setPhotoInput(photo) {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput() {
    return this.photoInput.getAttribute('value');
  }

  async setPhotoUrlInput(photoUrl) {
    await this.photoUrlInput.sendKeys(photoUrl);
  }

  async getPhotoUrlInput() {
    return this.photoUrlInput.getAttribute('value');
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

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
  }
  async clientSelectLastOption() {
    await this.clientSelect.all(by.tagName('option')).last().click();
  }

  async clientSelectOption(option) {
    await this.clientSelect.sendKeys(option);
  }

  getClientSelect() {
    return this.clientSelect;
  }

  async getClientSelectedOption() {
    return this.clientSelect.element(by.css('option:checked')).getText();
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
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAddressInput('address');
    expect(await this.getAddressInput()).to.match(/address/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTelephoneInput('telephone');
    expect(await this.getTelephoneInput()).to.match(/telephone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setContactNameInput('contactName');
    expect(await this.getContactNameInput()).to.match(/contactName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBranchEmailInput('branchEmail');
    expect(await this.getBranchEmailInput()).to.match(/branchEmail/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPhotoInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setPhotoUrlInput('photoUrl');
    expect(await this.getPhotoUrlInput()).to.match(/photoUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.clientSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
