import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeDesignation, defaultValue } from 'app/shared/model/employee-designation.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEDESIGNATION_LIST: 'employeeDesignation/FETCH_EMPLOYEEDESIGNATION_LIST',
  FETCH_EMPLOYEEDESIGNATION: 'employeeDesignation/FETCH_EMPLOYEEDESIGNATION',
  CREATE_EMPLOYEEDESIGNATION: 'employeeDesignation/CREATE_EMPLOYEEDESIGNATION',
  UPDATE_EMPLOYEEDESIGNATION: 'employeeDesignation/UPDATE_EMPLOYEEDESIGNATION',
  DELETE_EMPLOYEEDESIGNATION: 'employeeDesignation/DELETE_EMPLOYEEDESIGNATION',
  RESET: 'employeeDesignation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeDesignation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmployeeDesignationState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeDesignationState = initialState, action): EmployeeDesignationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEDESIGNATION):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEDESIGNATION):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEDESIGNATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEDESIGNATION):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEDESIGNATION):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEDESIGNATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEDESIGNATION):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEDESIGNATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEDESIGNATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/employee-designations';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeDesignation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION_LIST,
    payload: axios.get<IEmployeeDesignation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmployeeDesignation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDESIGNATION,
    payload: axios.get<IEmployeeDesignation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeDesignation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEDESIGNATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeDesignation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEDESIGNATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeDesignation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEDESIGNATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
