import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee-holiday.reducer';
import { IEmployeeHoliday } from 'app/shared/model/employee-holiday.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmployeeHolidayProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmployeeHoliday = (props: IEmployeeHolidayProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { employeeHolidayList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="employee-holiday-heading">
        <Translate contentKey="carePlannerApp.employeeHoliday.home.title">Employee Holidays</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.employeeHoliday.home.createLabel">Create new Employee Holiday</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeHolidayList && employeeHolidayList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.startDate">Start Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('endDate')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.endDate">End Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('employeeHolidayType')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.employeeHolidayType">Employee Holiday Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedDate')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.approvedDate">Approved Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requestedDate')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.requestedDate">Requested Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approved')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.approved">Approved</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.employeeHoliday.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.employeeHoliday.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.employeeHoliday.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeHolidayList.map((employeeHoliday, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employeeHoliday.id}`} color="link" size="sm">
                      {employeeHoliday.id}
                    </Button>
                  </td>
                  <td>{employeeHoliday.description}</td>
                  <td>
                    {employeeHoliday.startDate ? (
                      <TextFormat type="date" value={employeeHoliday.startDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeHoliday.endDate ? <TextFormat type="date" value={employeeHoliday.endDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.EmployeeHolidayType.${employeeHoliday.employeeHolidayType}`} />
                  </td>
                  <td>
                    {employeeHoliday.approvedDate ? (
                      <TextFormat type="date" value={employeeHoliday.approvedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeHoliday.requestedDate ? (
                      <TextFormat type="date" value={employeeHoliday.requestedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeHoliday.approved ? 'true' : 'false'}</td>
                  <td>{employeeHoliday.note}</td>
                  <td>
                    {employeeHoliday.lastUpdatedDate ? (
                      <TextFormat type="date" value={employeeHoliday.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeHoliday.clientId}</td>
                  <td>
                    {employeeHoliday.employeeEmployeeCode ? (
                      <Link to={`employee/${employeeHoliday.employeeId}`}>{employeeHoliday.employeeEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeHoliday.approvedByEmployeeCode ? (
                      <Link to={`employee/${employeeHoliday.approvedById}`}>{employeeHoliday.approvedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employeeHoliday.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${employeeHoliday.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${employeeHoliday.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.employeeHoliday.home.notFound">No Employee Holidays found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={employeeHolidayList && employeeHolidayList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ employeeHoliday }: IRootState) => ({
  employeeHolidayList: employeeHoliday.entities,
  loading: employeeHoliday.loading,
  totalItems: employeeHoliday.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeHoliday);
