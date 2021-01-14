import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class NotificationUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.notification.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#notification-title'));
  bodyInput: ElementFinder = element(by.css('input#notification-body'));
  notificationDateInput: ElementFinder = element(by.css('input#notification-notificationDate'));
  imageInput: ElementFinder = element(by.css('input#file_image'));
  imageUrlInput: ElementFinder = element(by.css('input#notification-imageUrl'));
  senderIdInput: ElementFinder = element(by.css('input#notification-senderId'));
  receiverIdInput: ElementFinder = element(by.css('input#notification-receiverId'));
  createdDateInput: ElementFinder = element(by.css('input#notification-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#notification-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#notification-clientId'));
  hasExtraDataInput: ElementFinder = element(by.css('input#notification-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  async setBodyInput(body) {
    await this.bodyInput.sendKeys(body);
  }

  async getBodyInput() {
    return this.bodyInput.getAttribute('value');
  }

  async setNotificationDateInput(notificationDate) {
    await this.notificationDateInput.sendKeys(notificationDate);
  }

  async getNotificationDateInput() {
    return this.notificationDateInput.getAttribute('value');
  }

  async setImageInput(image) {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput() {
    return this.imageInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl) {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput() {
    return this.imageUrlInput.getAttribute('value');
  }

  async setSenderIdInput(senderId) {
    await this.senderIdInput.sendKeys(senderId);
  }

  async getSenderIdInput() {
    return this.senderIdInput.getAttribute('value');
  }

  async setReceiverIdInput(receiverId) {
    await this.receiverIdInput.sendKeys(receiverId);
  }

  async getReceiverIdInput() {
    return this.receiverIdInput.getAttribute('value');
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
    await this.setTitleInput('title');
    expect(await this.getTitleInput()).to.match(/title/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBodyInput('body');
    expect(await this.getBodyInput()).to.match(/body/);
    await waitUntilDisplayed(this.saveButton);
    await this.setNotificationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getNotificationDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setImageInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setImageUrlInput('imageUrl');
    expect(await this.getImageUrlInput()).to.match(/imageUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setSenderIdInput('5');
    expect(await this.getSenderIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setReceiverIdInput('5');
    expect(await this.getReceiverIdInput()).to.eq('5');
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
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
