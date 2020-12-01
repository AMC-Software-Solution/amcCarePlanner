import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EmployeeAvailabilityUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.employeeAvailability.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  isAvailableForWorkWeekDaysInput: ElementFinder = element(by.css('input#employee-availability-isAvailableForWorkWeekDays'));
  minimumHoursPerWeekWeekDaysInput: ElementFinder = element(by.css('input#employee-availability-minimumHoursPerWeekWeekDays'));
  maximumHoursPerWeekWeekDaysInput: ElementFinder = element(by.css('input#employee-availability-maximumHoursPerWeekWeekDays'));
  isAvailableForWorkWeekEndsInput: ElementFinder = element(by.css('input#employee-availability-isAvailableForWorkWeekEnds'));
  minimumHoursPerWeekWeekEndsInput: ElementFinder = element(by.css('input#employee-availability-minimumHoursPerWeekWeekEnds'));
  maximumHoursPerWeekWeekEndsInput: ElementFinder = element(by.css('input#employee-availability-maximumHoursPerWeekWeekEnds'));
  leastPreferredShiftSelect: ElementFinder = element(by.css('select#employee-availability-leastPreferredShift'));
  employeeSelect: ElementFinder = element(by.css('select#employee-availability-employee'));

  getPageTitle() {
    return this.pageTitle;
  }

  getIsAvailableForWorkWeekDaysInput() {
    return this.isAvailableForWorkWeekDaysInput;
  }
  async setMinimumHoursPerWeekWeekDaysInput(minimumHoursPerWeekWeekDays) {
    await this.minimumHoursPerWeekWeekDaysInput.sendKeys(minimumHoursPerWeekWeekDays);
  }

  async getMinimumHoursPerWeekWeekDaysInput() {
    return this.minimumHoursPerWeekWeekDaysInput.getAttribute('value');
  }

  async setMaximumHoursPerWeekWeekDaysInput(maximumHoursPerWeekWeekDays) {
    await this.maximumHoursPerWeekWeekDaysInput.sendKeys(maximumHoursPerWeekWeekDays);
  }

  async getMaximumHoursPerWeekWeekDaysInput() {
    return this.maximumHoursPerWeekWeekDaysInput.getAttribute('value');
  }

  getIsAvailableForWorkWeekEndsInput() {
    return this.isAvailableForWorkWeekEndsInput;
  }
  async setMinimumHoursPerWeekWeekEndsInput(minimumHoursPerWeekWeekEnds) {
    await this.minimumHoursPerWeekWeekEndsInput.sendKeys(minimumHoursPerWeekWeekEnds);
  }

  async getMinimumHoursPerWeekWeekEndsInput() {
    return this.minimumHoursPerWeekWeekEndsInput.getAttribute('value');
  }

  async setMaximumHoursPerWeekWeekEndsInput(maximumHoursPerWeekWeekEnds) {
    await this.maximumHoursPerWeekWeekEndsInput.sendKeys(maximumHoursPerWeekWeekEnds);
  }

  async getMaximumHoursPerWeekWeekEndsInput() {
    return this.maximumHoursPerWeekWeekEndsInput.getAttribute('value');
  }

  async setLeastPreferredShiftSelect(leastPreferredShift) {
    await this.leastPreferredShiftSelect.sendKeys(leastPreferredShift);
  }

  async getLeastPreferredShiftSelect() {
    return this.leastPreferredShiftSelect.element(by.css('option:checked')).getText();
  }

  async leastPreferredShiftSelectLastOption() {
    await this.leastPreferredShiftSelect.all(by.tagName('option')).last().click();
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
    const selectedIsAvailableForWorkWeekDays = await this.getIsAvailableForWorkWeekDaysInput().isSelected();
    if (selectedIsAvailableForWorkWeekDays) {
      await this.getIsAvailableForWorkWeekDaysInput().click();
      expect(await this.getIsAvailableForWorkWeekDaysInput().isSelected()).to.be.false;
    } else {
      await this.getIsAvailableForWorkWeekDaysInput().click();
      expect(await this.getIsAvailableForWorkWeekDaysInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setMinimumHoursPerWeekWeekDaysInput('5');
    expect(await this.getMinimumHoursPerWeekWeekDaysInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMaximumHoursPerWeekWeekDaysInput('5');
    expect(await this.getMaximumHoursPerWeekWeekDaysInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedIsAvailableForWorkWeekEnds = await this.getIsAvailableForWorkWeekEndsInput().isSelected();
    if (selectedIsAvailableForWorkWeekEnds) {
      await this.getIsAvailableForWorkWeekEndsInput().click();
      expect(await this.getIsAvailableForWorkWeekEndsInput().isSelected()).to.be.false;
    } else {
      await this.getIsAvailableForWorkWeekEndsInput().click();
      expect(await this.getIsAvailableForWorkWeekEndsInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setMinimumHoursPerWeekWeekEndsInput('5');
    expect(await this.getMinimumHoursPerWeekWeekEndsInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMaximumHoursPerWeekWeekEndsInput('5');
    expect(await this.getMaximumHoursPerWeekWeekEndsInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.leastPreferredShiftSelectLastOption();
    await this.employeeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
