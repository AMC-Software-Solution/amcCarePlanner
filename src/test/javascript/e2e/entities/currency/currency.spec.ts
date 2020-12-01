import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CurrencyComponentsPage from './currency.page-object';
import CurrencyUpdatePage from './currency-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Currency e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let currencyComponentsPage: CurrencyComponentsPage;
  let currencyUpdatePage: CurrencyUpdatePage;

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
    currencyComponentsPage = new CurrencyComponentsPage();
    currencyComponentsPage = await currencyComponentsPage.goToPage(navBarPage);
  });

  it('should load Currencies', async () => {
    expect(await currencyComponentsPage.title.getText()).to.match(/Currencies/);
    expect(await currencyComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Currencies', async () => {
    const beforeRecordsCount = (await isVisible(currencyComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(currencyComponentsPage.table);
    currencyUpdatePage = await currencyComponentsPage.goToCreateCurrency();
    await currencyUpdatePage.enterData();

    expect(await currencyComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(currencyComponentsPage.table);
    await waitUntilCount(currencyComponentsPage.records, beforeRecordsCount + 1);
    expect(await currencyComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await currencyComponentsPage.deleteCurrency();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(currencyComponentsPage.records, beforeRecordsCount);
      expect(await currencyComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(currencyComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
