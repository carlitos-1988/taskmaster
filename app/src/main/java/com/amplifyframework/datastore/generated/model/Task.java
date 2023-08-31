package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Task type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTeam", fields = {"teamID","taskName"})
public final class Task implements Model {
  public static final QueryField ID = field("Task", "id");
  public static final QueryField TASK_NAME = field("Task", "taskName");
  public static final QueryField DESCRIPTION = field("Task", "description");
  public static final QueryField DATE_CREATED = field("Task", "dateCreated");
  public static final QueryField TASK_CATEGORY = field("Task", "taskCategory");
  public static final QueryField CONTACT_TEAM = field("Task", "teamID");
  public static final QueryField TASK_IMAGE_S3_KEY = field("Task", "taskImageS3Key");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String taskName;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime dateCreated;
  private final @ModelField(targetType="TaskCategoryEnum") TaskCategoryEnum taskCategory;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamID", targetNames = {"teamID"}, type = Team.class) Team contactTeam;
  private final @ModelField(targetType="String") String taskImageS3Key;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTaskName() {
      return taskName;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public TaskCategoryEnum getTaskCategory() {
      return taskCategory;
  }
  
  public Team getContactTeam() {
      return contactTeam;
  }
  
  public String getTaskImageS3Key() {
      return taskImageS3Key;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Task(String id, String taskName, String description, Temporal.DateTime dateCreated, TaskCategoryEnum taskCategory, Team contactTeam, String taskImageS3Key) {
    this.id = id;
    this.taskName = taskName;
    this.description = description;
    this.dateCreated = dateCreated;
    this.taskCategory = taskCategory;
    this.contactTeam = contactTeam;
    this.taskImageS3Key = taskImageS3Key;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Task task = (Task) obj;
      return ObjectsCompat.equals(getId(), task.getId()) &&
              ObjectsCompat.equals(getTaskName(), task.getTaskName()) &&
              ObjectsCompat.equals(getDescription(), task.getDescription()) &&
              ObjectsCompat.equals(getDateCreated(), task.getDateCreated()) &&
              ObjectsCompat.equals(getTaskCategory(), task.getTaskCategory()) &&
              ObjectsCompat.equals(getContactTeam(), task.getContactTeam()) &&
              ObjectsCompat.equals(getTaskImageS3Key(), task.getTaskImageS3Key()) &&
              ObjectsCompat.equals(getCreatedAt(), task.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), task.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTaskName())
      .append(getDescription())
      .append(getDateCreated())
      .append(getTaskCategory())
      .append(getContactTeam())
      .append(getTaskImageS3Key())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Task {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("taskName=" + String.valueOf(getTaskName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("taskCategory=" + String.valueOf(getTaskCategory()) + ", ")
      .append("contactTeam=" + String.valueOf(getContactTeam()) + ", ")
      .append("taskImageS3Key=" + String.valueOf(getTaskImageS3Key()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TaskNameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Task justId(String id) {
    return new Task(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      taskName,
      description,
      dateCreated,
      taskCategory,
      contactTeam,
      taskImageS3Key);
  }
  public interface TaskNameStep {
    BuildStep taskName(String taskName);
  }
  

  public interface BuildStep {
    Task build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep dateCreated(Temporal.DateTime dateCreated);
    BuildStep taskCategory(TaskCategoryEnum taskCategory);
    BuildStep contactTeam(Team contactTeam);
    BuildStep taskImageS3Key(String taskImageS3Key);
  }
  

  public static class Builder implements TaskNameStep, BuildStep {
    private String id;
    private String taskName;
    private String description;
    private Temporal.DateTime dateCreated;
    private TaskCategoryEnum taskCategory;
    private Team contactTeam;
    private String taskImageS3Key;
    @Override
     public Task build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Task(
          id,
          taskName,
          description,
          dateCreated,
          taskCategory,
          contactTeam,
          taskImageS3Key);
    }
    
    @Override
     public BuildStep taskName(String taskName) {
        Objects.requireNonNull(taskName);
        this.taskName = taskName;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    @Override
     public BuildStep taskCategory(TaskCategoryEnum taskCategory) {
        this.taskCategory = taskCategory;
        return this;
    }
    
    @Override
     public BuildStep contactTeam(Team contactTeam) {
        this.contactTeam = contactTeam;
        return this;
    }
    
    @Override
     public BuildStep taskImageS3Key(String taskImageS3Key) {
        this.taskImageS3Key = taskImageS3Key;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String taskName, String description, Temporal.DateTime dateCreated, TaskCategoryEnum taskCategory, Team contactTeam, String taskImageS3Key) {
      super.id(id);
      super.taskName(taskName)
        .description(description)
        .dateCreated(dateCreated)
        .taskCategory(taskCategory)
        .contactTeam(contactTeam)
        .taskImageS3Key(taskImageS3Key);
    }
    
    @Override
     public CopyOfBuilder taskName(String taskName) {
      return (CopyOfBuilder) super.taskName(taskName);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder taskCategory(TaskCategoryEnum taskCategory) {
      return (CopyOfBuilder) super.taskCategory(taskCategory);
    }
    
    @Override
     public CopyOfBuilder contactTeam(Team contactTeam) {
      return (CopyOfBuilder) super.contactTeam(contactTeam);
    }
    
    @Override
     public CopyOfBuilder taskImageS3Key(String taskImageS3Key) {
      return (CopyOfBuilder) super.taskImageS3Key(taskImageS3Key);
    }
  }
  

  public static class TaskIdentifier extends ModelIdentifier<Task> {
    private static final long serialVersionUID = 1L;
    public TaskIdentifier(String id) {
      super(id);
    }
  }
  
}
