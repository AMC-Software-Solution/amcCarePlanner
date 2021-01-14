import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RelationshipTypeComponentsPage from './relationship-type.page-object';
import RelationshipTypeUpdatePage from './relationship-type-update.page-object';
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

describe('RelationshipType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let relationshipTypeComponentsPage: RelationshipTypeComponentsPage;
  let relationshipTypeUpdatePage: RelationshipTypeUpdatePage;

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
    relationshipTypeComponentsPage = new RelationshipTypeComponentsPage();
    relationshipTypeComponentsPage = await relationshipTypeComponentsPage.goToPage(navBarPage);
  });

  it('should load RelationshipTypes', async () => {
    expect(await relationshipTypeComponentsPage.title.getText()).to.match(/Relationship Types/);
    expect(await relationshipTypeComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete RelationshipTypes', async () => {
    const beforeRecordsCount = (await isVisible(relationshipTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(relationshipTypeComponentsPage.table);
    relationshipTypeUpdatePage = await relationshipTypeComponentsPage.goToCreateRelationshipType();
    await relationshipTypeUpdatePage.enterData();

    expect(await relationshipTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(relationshipTypeComponentsPage.table);
    await waitUntilCount(relationshipTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await relationshipTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await relationshipTypeComponentsPage.deleteRelationshipType();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(relationshipTypeComponentsPage.records, beforeRecordsCount);
      expect(await relationshipTypeComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(relationshipTypeComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
