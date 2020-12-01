import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceUserLocationComponentsPage from './service-user-location.page-object';
import ServiceUserLocationUpdatePage from './service-user-location-update.page-object';
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

describe('ServiceUserLocation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceUserLocationComponentsPage: ServiceUserLocationComponentsPage;
  let serviceUserLocationUpdatePage: ServiceUserLocationUpdatePage;

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
    serviceUserLocationComponentsPage = new ServiceUserLocationComponentsPage();
    serviceUserLocationComponentsPage = await serviceUserLocationComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceUserLocations', async () => {
    expect(await serviceUserLocationComponentsPage.title.getText()).to.match(/Service User Locations/);
    expect(await serviceUserLocationComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceUserLocations', async () => {
    const beforeRecordsCount = (await isVisible(serviceUserLocationComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceUserLocationComponentsPage.table);
    serviceUserLocationUpdatePage = await serviceUserLocationComponentsPage.goToCreateServiceUserLocation();
    await serviceUserLocationUpdatePage.enterData();

    expect(await serviceUserLocationComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceUserLocationComponentsPage.table);
    await waitUntilCount(serviceUserLocationComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceUserLocationComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceUserLocationComponentsPage.deleteServiceUserLocation();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceUserLocationComponentsPage.records, beforeRecordsCount);
      expect(await serviceUserLocationComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceUserLocationComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
