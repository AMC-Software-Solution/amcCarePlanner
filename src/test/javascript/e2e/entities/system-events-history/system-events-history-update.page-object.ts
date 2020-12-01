import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class SystemEventsHistoryUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.systemEventsHistory.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  eventNameInput: ElementFinder = element(by.css('input#system-events-history-eventName'));
  eventDateInput: ElementFinder = element(by.css('input#system-events-history-eventDate'));
  eventApiInput: ElementFinder = element(by.css('input#system-events-history-eventApi'));
  ipAddressInput: ElementFinder = element(by.css('input#system-events-history-ipAddress'));
  eventNoteInput: ElementFinder = element(by.css('input#system-events-history-eventNote'));
  eventEntityNameInput: ElementFinder = element(by.css('input#system-events-history-eventEntityName'));
  eventEntityIdInput: ElementFinder = element(by.css('input#system-events-history-eventEntityId'));
  isSuspeciousInput: ElementFinder = element(by.css('input#system-events-history-isSuspecious'));
  clientIdInput: ElementFinder = element(by.css('input#system-events-history-clientId'));
  triggedBySelect: ElementFinder = element(by.css('select#system-events-history-triggedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setEventNameInput(eventName) {
    await this.eventNameInput.sendKeys(eventName);
  }

  async getEventNameInput() {
    return this.eventNameInput.getAttribute('value');
  }

  async setEventDateInput(eventDate) {
    await this.eventDateInput.sendKeys(eventDate);
  }

  async getEventDateInput() {
    return this.eventDateInput.getAttribute('value');
  }

  async setEventApiInput(eventApi) {
    await this.eventApiInput.sendKeys(eventApi);
  }

  async getEventApiInput() {
    return this.eventApiInput.getAttribute('value');
  }

  async setIpAddressInput(ipAddress) {
    await this.ipAddressInput.sendKeys(ipAddress);
  }

  async getIpAddressInput() {
    return this.ipAddressInput.getAttribute('value');
  }

  async setEventNoteInput(eventNote) {
    await this.eventNoteInput.sendKeys(eventNote);
  }

  async getEventNoteInput() {
    return this.eventNoteInput.getAttribute('value');
  }

  async setEventEntityNameInput(eventEntityName) {
    await this.eventEntityNameInput.sendKeys(eventEntityName);
  }

  async getEventEntityNameInput() {
    return this.eventEntityNameInput.getAttribute('value');
  }

  async setEventEntityIdInput(eventEntityId) {
    await this.eventEntityIdInput.sendKeys(eventEntityId);
  }

  async getEventEntityIdInput() {
    return this.eventEntityIdInput.getAttribute('value');
  }

  getIsSuspeciousInput() {
    return this.isSuspeciousInput;
  }
  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  async triggedBySelectLastOption() {
    await this.triggedBySelect.all(by.tagName('option')).last().click();
  }

  async triggedBySelectOption(option) {
    await this.triggedBySelect.sendKeys(option);
  }

  getTriggedBySelect() {
    return this.triggedBySelect;
  }

  async getTriggedBySelectedOption() {
    return this.triggedBySelect.element(by.css('option:checked')).getText();
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
    await this.setEventNameInput('eventName');
    expect(await this.getEventNameInput()).to.match(/eventName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEventDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getEventDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setEventApiInput('eventApi');
    expect(await this.getEventApiInput()).to.match(/eventApi/);
    await waitUntilDisplayed(this.saveButton);
    await this.setIpAddressInput('ipAddress');
    expect(await this.getIpAddressInput()).to.match(/ipAddress/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEventNoteInput('eventNote');
    expect(await this.getEventNoteInput()).to.match(/eventNote/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEventEntityNameInput('eventEntityName');
    expect(await this.getEventEntityNameInput()).to.match(/eventEntityName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEventEntityIdInput('5');
    expect(await this.getEventEntityIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedIsSuspecious = await this.getIsSuspeciousInput().isSelected();
    if (selectedIsSuspecious) {
      await this.getIsSuspeciousInput().click();
      expect(await this.getIsSuspeciousInput().isSelected()).to.be.false;
    } else {
      await this.getIsSuspeciousInput().click();
      expect(await this.getIsSuspeciousInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.triggedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
