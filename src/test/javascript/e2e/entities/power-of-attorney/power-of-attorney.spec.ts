import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PowerOfAttorneyComponentsPage from './power-of-attorney.page-object';
import PowerOfAttorneyUpdatePage from './power-of-attorney-update.page-object';
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

describe('PowerOfAttorney e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let powerOfAttorneyComponentsPage: PowerOfAttorneyComponentsPage;
  let powerOfAttorneyUpdatePage: PowerOfAttorneyUpdatePage;

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
    powerOfAttorneyComponentsPage = new PowerOfAttorneyComponentsPage();
    powerOfAttorneyComponentsPage = await powerOfAttorneyComponentsPage.goToPage(navBarPage);
  });

  it('should load PowerOfAttorneys', async () => {
    expect(await powerOfAttorneyComponentsPage.title.getText()).to.match(/Power Of Attorneys/);
    expect(await powerOfAttorneyComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete PowerOfAttorneys', async () => {
    const beforeRecordsCount = (await isVisible(powerOfAttorneyComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(powerOfAttorneyComponentsPage.table);
    powerOfAttorneyUpdatePage = await powerOfAttorneyComponentsPage.goToCreatePowerOfAttorney();
    await powerOfAttorneyUpdatePage.enterData();

    expect(await powerOfAttorneyComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(powerOfAttorneyComponentsPage.table);
    await waitUntilCount(powerOfAttorneyComponentsPage.records, beforeRecordsCount + 1);
    expect(await powerOfAttorneyComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await powerOfAttorneyComponentsPage.deletePowerOfAttorney();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(powerOfAttorneyComponentsPage.records, beforeRecordsCount);
      expect(await powerOfAttorneyComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(powerOfAttorneyComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
