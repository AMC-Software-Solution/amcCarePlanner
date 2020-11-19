import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceUserEventComponentsPage from './service-user-event.page-object';
import ServiceUserEventUpdatePage from './service-user-event-update.page-object';
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

describe('ServiceUserEvent e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceUserEventComponentsPage: ServiceUserEventComponentsPage;
  let serviceUserEventUpdatePage: ServiceUserEventUpdatePage;

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
    serviceUserEventComponentsPage = new ServiceUserEventComponentsPage();
    serviceUserEventComponentsPage = await serviceUserEventComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceUserEvents', async () => {
    expect(await serviceUserEventComponentsPage.title.getText()).to.match(/Service User Events/);
    expect(await serviceUserEventComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceUserEvents', async () => {
    const beforeRecordsCount = (await isVisible(serviceUserEventComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceUserEventComponentsPage.table);
    serviceUserEventUpdatePage = await serviceUserEventComponentsPage.goToCreateServiceUserEvent();
    await serviceUserEventUpdatePage.enterData();

    expect(await serviceUserEventComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceUserEventComponentsPage.table);
    await waitUntilCount(serviceUserEventComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceUserEventComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceUserEventComponentsPage.deleteServiceUserEvent();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceUserEventComponentsPage.records, beforeRecordsCount);
      expect(await serviceUserEventComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceUserEventComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
