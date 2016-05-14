package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Schedulers
 * 
 * @invar  The faction of each Scheduler must be a valid faction for any
 *         Scheduler.
 *       | isValidFaction(getFaction())
 *
 * @invar   Each Scheduler must have proper Tasks.
 *        | hasProperTasks()
 *        
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Scheduler {

	/**
	 * Initialize this new Scheduler with given faction.
	 *
	 * @param  faction
	 *         The faction for this new Scheduler.
	 * @effect The faction of this new Scheduler is set to
	 *         the given faction.
	 *       | this.setFaction(faction)
	 *       
 	 * @post   This new Scheduler has no Tasks yet.
	 *       | new.getNbTasks() == 0
	 */
	public Scheduler(Faction faction)
			throws IllegalArgumentException {
		this.setFaction(faction);
	}


	/**
	 * Return the faction of this Scheduler.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}

	/**
	 * Check whether the given faction is a valid faction for
	 * any Scheduler.
	 *  
	 * @param  faction
	 *         The faction to check.
	 * @return 
	 *       | result == (faction != null)
	*/
	public static boolean isValidFaction(Faction faction) {
		return (faction != null);
	}
	
	/**
	 * Set the faction of this Scheduler to the given faction.
	 * 
	 * @param  faction
	 *         The new faction for this Scheduler.
	 * @post   The faction of this new Scheduler is equal to
	 *         the given faction.
	 *       | new.getFaction() == faction
	 * @throws IllegalArgumentException
	 *         The given faction is not a valid faction for any
	 *         Scheduler.
	 *       | ! isValidFaction(getFaction())
	 */
	@Raw
	public void setFaction(Faction faction) 
			throws IllegalArgumentException {
		if (! isValidFaction(faction))
			throw new IllegalArgumentException();
		this.faction = faction;
	}

	/**
	 * Variable registering the faction of this Scheduler.
	 */
	private Faction faction;

	/**
	 * Return the Task associated with this Scheduler at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the Task to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of Tasks for this Scheduler.
	 *       | (index < 1) || (index > getNbTasks())
	 */
	@Basic
	@Raw
	public Task getTaskAt(int index) throws IndexOutOfBoundsException {
		return tasks.get(index - 1);
	}

	/**
	 * Return the number of Tasks associated with this Scheduler.
	 */
	@Basic
	@Raw
	public int getNbTasks() {
		return tasks.size();
	}

	/**
	 * Check whether this Scheduler can have the given Task
	 * as one of its Tasks.
	 * 
	 * @param  task
	 *         The Task to check.
	 * @return True if and only if the given Task is effective, not terminated
	 *         and that Task can have this Scheduler as its Scheduler.
	 *       | result ==
	 *       |   (task != null) &&
	 *       |   task.canHaveAsScheduler(this) &&
	 *       | 	 !task.isTerminated()
	 */
	@Raw
	public boolean canHaveAsTask(Task task) {
		return (task != null) && (task.canHaveAsScheduler(this)) && !task.isTerminated();
	}

	/**
	 * Check whether this Scheduler can have the given Task
	 * as one of its Tasks at the given index.
	 * 
	 * @param  task
	 *         The Task to check.
	 * @return False if the given index is not positive or exceeds the
	 *         number of Tasks for this Scheduler + 1.
	 *       | if ( (index < 1) || (index > getNbTasks()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this Scheduler cannot have the given
	 *         Task as one of its Tasks.
	 *       | else if ( ! this.canHaveAsTask(task) )
	 *       |   then result == false
	 *         Otherwise, true if and only if the given Task is
	 *         not registered at another index than the given index.
	 *       | else result ==
	 *       |   for each I in 1..getNbTasks():
	 *       |     (index == I) || (getTaskAt(I) != task)
	 */
	@Raw
	public boolean canHaveAsTaskAt(Task task, int index) {
		if ((index < 1) || (index > getNbTasks() + 1))
			return false;
		if (!this.canHaveAsTask(task))
			return false;
		for (int i = 1; i < getNbTasks(); i++)
			if ((i != index) && (getTaskAt(i) == task))
				return false;
		return true;
	}

	/**
	 * Check whether this Scheduler has proper Tasks attached to it.
	 * 
	 * @return True if and only if this Scheduler can have each of the
	 *         Tasks attached to it as a Task at the given index,
	 *         and if each of these Tasks references this Scheduler as
	 *         the Scheduler to which they are attached.
	 *       | result ==
	 *       |   for each I in 1..getNbTasks():
	 *       |     ( this.canHaveAsTaskAt(getTaskAt(I) &&
	 *       |       (getTaskAt(I).hasAsScheduler(this)) )
	 */
	public boolean hasProperTasks() {
		for (int i = 1; i <= getNbTasks(); i++) {
			if (!canHaveAsTaskAt(getTaskAt(i), i))
				return false;
			if (!getTaskAt(i).hasAsScheduler(this))
				return false;
		}
		return true;
	}

	/**
	 * Check whether this Scheduler has the given Task as one of its
	 * Tasks.
	 * 
	 * @param  task
	 *         The Task to check.
	 * @return The given Task is registered at some position as
	 *         a Task of this Scheduler.
	 *       | for some I in 1..getNbTasks():
	 *       |   getTaskAt(I) == task
	 */
	public boolean hasAsTask(@Raw Task task) {
		return tasks.contains(task);
	}

	/**
	 * Add the given Task to the list of Tasks of this Scheduler.
	 * 
	 * @param  task
	 *         The Task to be added.
	 *        
	 * @post   The number of Tasks of this Scheduler is
	 *         incremented by 1.
	 *       | new.getNbTasks() == getNbTasks() + 1
	 * @post   This Scheduler has the given Task as its very last Task.
	 *       | new.getTaskAt(getNbTasks()+1) == task
	 * @throws IllegalArgumentException
	 * 		   The given Task is effective and already references
	 *         this Scheduler, and this Scheduler does not yet have the given
	 *         Task as one of its Tasks. Else, throw exception
	 *       | (task == null) || (task.getScheduler() != this) ||
	 *       | (this.hasAsTask(task))
	 */
	public void addTask(@Raw Task task) {
		if ((task == null) || (!task.hasAsScheduler(this)) && (this.hasAsTask(task))){
			throw new IllegalArgumentException("task is not valid for this scheduler");
		}
		tasks.add(task);
		Collections.sort(tasks);
	}
	
	
	public void scheduleTask(Task task){
		task.addScheduler(this);
		this.addTask(task);
	}
	
	/**
	 * add multiple Tasks to this Scheduler
	 * 
	 * @param tasks
	 * 		  A list with all the tasks to be added
	 * @effect The tasks are added to this scheduler
	 * 		   for(Task task in tasks)
	 * 		   do  this.addTask(task)
	 */
	public void addMultipleTasks(@Raw List<Task> tasks){
		for(Task task: tasks){
			this.addTask(task);
		}
	}

	/**
	 * Remove the given Task from the list of Tasks of this Scheduler.
	 * TODO: herzien throw exception
	 * 
	 * @param  task
	 *         The Task to be removed.
	 
	 * @post   The number of Tasks of this Scheduler is
	 *         decremented by 1.
	 *       | new.getNbTasks() == getNbTasks() - 1
	 * @post   This Scheduler no longer has the given Task as
	 *         one of its Tasks.
	 *       | ! new.hasAsTask(task)
	 * @post   All Tasks registered at an index beyond the index at
	 *         which the given Task was registered, are shifted
	 *         one position to the left.
	 *       | for each I,J in 1..getNbTasks():
	 *       |   if ( (getTaskAt(I) == task) and (I < J) )
	 *       |     then new.getTaskAt(J-1) == getTaskAt(J)
	 * @throws IllegalArgumentException   
	 * 		   The given Task is effective, this Scheduler has the
	 *         given Task as one of its Tasks, and the given
	 *         Task does not reference any Scheduler. 
	 *         Else throw the exception
	 *       | (task == null) ||
	 *       | !this.hasAsTask(task) ||
	 *       | (task.getScheduler() != null)
	 */
	@Raw
	public void removeTask(Task task) {
		if ((task == null) || !this.hasAsTask(task) || (!task.hasAsScheduler(this))){
			throw new IllegalArgumentException("tasks can't be removed");
		}
		tasks.remove(task);
	}
	
	/**
	 * remove all the tasks listed from this Scheduler
	 * 
	 * @param tasks
	 * 		  List of the tasks to be removed
	 * 
	 * @effect The tasks are removed
	 * 			for(Task task in tasks)
	 * 			do this.removeTask(task)
	 */
	public void removeMultipleTasks(List<Task> tasks){
		for(Task task: tasks){
			this.removeTask(task);
		}
	}

	/**
	 * Variable referencing a list collecting all the Tasks
	 * of this Scheduler.
	 * 
	 * @invar  The referenced list is effective.
	 *       | tasks != null
	 * @invar  Each Task registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each task in tasks:
	 *       |   ( (task != null) &&
	 *       |     (! task.isTerminated()) )
	 * @invar  No Task is registered at several positions
	 *         in the referenced list.
	 *       | for each I,J in 0..tasks.size()-1:
	 *       |   ( (I == J) ||
	 *       |     (tasks.get(I) != tasks.get(J))
	 */
	private final List<Task> tasks = new ArrayList<Task>();

	
	/**
	 * Replace a task in this scheduler by another task
	 * 
	 * @param original
	 * 		  The Task already in this scheduler
	 * @param replacement
	 * 		  The new Task
	 * @throws  IllegalArgumentException
	 * 			Original is not a task in this scheduler, or replacement can't be in this scheduler
	 * 			| (this.hasAsTask(original)) || (!this.canHaveAsTask(replacement)
	 * @post original is no longer part of this scheduler
	 * 		 | new.hasAsTask(original) == false
	 * 		 | (new original).hasAsScheduler(new) == false
	 * @post replacement is part of this scheduler
	 * 		 | new.hasAsTask(replacement) == true
	 * 		 | (new replacement).hasAsScheduler(new) == true
	 *
	 * @post if original was being executed by a Unit, it isn't now
	 * 		 | if(original.getUnit != null)
	 * 		 | then original.getUnit().setTask(null)
	 *		 |		original.setUnit(null)
	 *
	 */
	public void replaceTask(Task original, Task replacement) throws IllegalArgumentException{
		if (this.hasAsTask(original))
			throw new IllegalArgumentException("the given task is not part of this scheduler");
		if(!this.canHaveAsTask(replacement))
			throw new IllegalArgumentException("can't have this task as task");
		
		if(original.getUnit() != null){
			original.getUnit().setTask(null);
			original.setUnit(null);
		}
		
		original.removeScheduler(this);
		this.removeTask(original);
		replacement.addScheduler(this);
		this.addTask(replacement);
	}
	
	public boolean arePartOf(Collection<Task> tasks){
		for(Task task: tasks){
			if(!this.hasAsTask(task))
				return false;
		}
		return true;
	}
	
	
	public Iterator<Task> iterator(){
		return new Iterator<Task>(){

			@Override
			public boolean hasNext() {
				return this.index-1 < getNbTasks();
			}

			@Override
			public Task next() {
				index += 1;
				return getTaskAt(index-1);
			}
			
			private int index = 1;
			
		};
	}
	
	public Task getNextAvailableTask(){
		Iterator<Task> iterator = this.iterator();
		while(iterator.hasNext()){
			Task task = iterator.next();
			if(!task.isAssigned()){
				return task;
			}
		}
		return null;
	}
	
	/**
	 * returns all tasks of this scheduler as a list
	 * 
	 * @return a list of all tasks
	 * 		   | result == this.tasks
	 */
	public List<Task> getAllTasks(){
		List<Task> list = new ArrayList<Task>();
		list.addAll(this.tasks);
		Collections.sort(list);
		return list;
	}
	
	public void interruptTaskOf(Unit unit){
		unit.interruptTask();
	}
	
	public void assignNextTaskTo(Unit unit){
		Task task = this.getNextAvailableTask();
		if(task != null)
			task.assignTo(unit);
	}
	
	public void sortTasks(){
		Collections.sort(this.tasks);
	}
}
