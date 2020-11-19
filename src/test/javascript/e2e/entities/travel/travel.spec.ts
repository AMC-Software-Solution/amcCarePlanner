import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TravelComponentsPage from './travel.page-object';
import TravelUpdatePage from './travel-update.page-object';
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

describe('Travel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let travelComponentsPage: TravelComponentsPage;
  let travelUpdatePage: TravelUpdatePage;

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
    travelComponentsPage = new TravelComponentsPage();
    travelComponentsPage = await travelComponentsPage.goToPage(navBarPage);
  });

  it('should load Travels', async () => {
    expect(await travelComponentsPage.title.getText()).to.match(/Travels/);
    expect(await travelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Travels', async () => {
    const beforeRecordsCount = (await isVisible(travelComponentsPage.noRecords)) ? 0 : await getRecordsCount(travelComponentsPage.table);
    travelUpdatePage = await travelComponentsPage.goToCreateTravel();
    await travelUpdatePage.enterData();

    expect(await travelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(travelComponentsPage.table);
    await waitUntilCount(travelComponentsPage.records, beforeRecordsCount + 1);
    expect(await travelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await travelComponentsPage.deleteTravel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(travelComponentsPage.records, beforeRecordsCount);
      expect(await travelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(travelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
