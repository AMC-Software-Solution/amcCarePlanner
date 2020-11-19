import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TimesheetComponentsPage from './timesheet.page-object';
import TimesheetUpdatePage from './timesheet-update.page-object';
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

describe('Timesheet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let timesheetComponentsPage: TimesheetComponentsPage;
  let timesheetUpdatePage: TimesheetUpdatePage;

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
    timesheetComponentsPage = new TimesheetComponentsPage();
    timesheetComponentsPage = await timesheetComponentsPage.goToPage(navBarPage);
  });

  it('should load Timesheets', async () => {
    expect(await timesheetComponentsPage.title.getText()).to.match(/Timesheets/);
    expect(await timesheetComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Timesheets', async () => {
    const beforeRecordsCount = (await isVisible(timesheetComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(timesheetComponentsPage.table);
    timesheetUpdatePage = await timesheetComponentsPage.goToCreateTimesheet();
    await timesheetUpdatePage.enterData();

    expect(await timesheetComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(timesheetComponentsPage.table);
    await waitUntilCount(timesheetComponentsPage.records, beforeRecordsCount + 1);
    expect(await timesheetComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await timesheetComponentsPage.deleteTimesheet();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(timesheetComponentsPage.records, beforeRecordsCount);
      expect(await timesheetComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(timesheetComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
