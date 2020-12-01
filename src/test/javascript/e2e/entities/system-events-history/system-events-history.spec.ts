import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SystemEventsHistoryComponentsPage from './system-events-history.page-object';
import SystemEventsHistoryUpdatePage from './system-events-history-update.page-object';
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

describe('SystemEventsHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let systemEventsHistoryComponentsPage: SystemEventsHistoryComponentsPage;
  let systemEventsHistoryUpdatePage: SystemEventsHistoryUpdatePage;

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
    systemEventsHistoryComponentsPage = new SystemEventsHistoryComponentsPage();
    systemEventsHistoryComponentsPage = await systemEventsHistoryComponentsPage.goToPage(navBarPage);
  });

  it('should load SystemEventsHistories', async () => {
    expect(await systemEventsHistoryComponentsPage.title.getText()).to.match(/System Events Histories/);
    expect(await systemEventsHistoryComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete SystemEventsHistories', async () => {
    const beforeRecordsCount = (await isVisible(systemEventsHistoryComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(systemEventsHistoryComponentsPage.table);
    systemEventsHistoryUpdatePage = await systemEventsHistoryComponentsPage.goToCreateSystemEventsHistory();
    await systemEventsHistoryUpdatePage.enterData();

    expect(await systemEventsHistoryComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(systemEventsHistoryComponentsPage.table);
    await waitUntilCount(systemEventsHistoryComponentsPage.records, beforeRecordsCount + 1);
    expect(await systemEventsHistoryComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await systemEventsHistoryComponentsPage.deleteSystemEventsHistory();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(systemEventsHistoryComponentsPage.records, beforeRecordsCount);
      expect(await systemEventsHistoryComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(systemEventsHistoryComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
