import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmployeeHolidayComponentsPage from './employee-holiday.page-object';
import EmployeeHolidayUpdatePage from './employee-holiday-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('EmployeeHoliday e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeHolidayComponentsPage: EmployeeHolidayComponentsPage;
  let employeeHolidayUpdatePage: EmployeeHolidayUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    employeeHolidayComponentsPage = new EmployeeHolidayComponentsPage();
    employeeHolidayComponentsPage = await employeeHolidayComponentsPage.goToPage(navBarPage);
  });

  it('should load EmployeeHolidays', async () => {
    expect(await employeeHolidayComponentsPage.title.getText()).to.match(/Employee Holidays/);
    expect(await employeeHolidayComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EmployeeHolidays', async () => {
    const beforeRecordsCount = (await isVisible(employeeHolidayComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(employeeHolidayComponentsPage.table);
    employeeHolidayUpdatePage = await employeeHolidayComponentsPage.goToCreateEmployeeHoliday();
    await employeeHolidayUpdatePage.enterData();

    expect(await employeeHolidayComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(employeeHolidayComponentsPage.table);
    await waitUntilCount(employeeHolidayComponentsPage.records, beforeRecordsCount + 1);
    expect(await employeeHolidayComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await employeeHolidayComponentsPage.deleteEmployeeHoliday();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(employeeHolidayComponentsPage.records, beforeRecordsCount);
      expect(await employeeHolidayComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(employeeHolidayComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
