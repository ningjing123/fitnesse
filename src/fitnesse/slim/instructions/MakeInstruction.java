package fitnesse.slim.instructions;

import fitnesse.slim.SlimException;

import java.util.Arrays;

public class MakeInstruction extends Instruction<MakeInstruction.MakeExecutor> {
  public static final String INSTRUCTION = "make";
  private final String instanceName;
  private final String className;
  private final Object[] args;

  public MakeInstruction(String id, String instanceName, String className) {
    this(id, instanceName, className, new Object[]{});
  }

  public MakeInstruction(String id, String instanceName, String className, Object[] args) {
    super(id);
    this.instanceName = instanceName;
    this.className = className;
    this.args = args;
  }

  @Override
  protected Object executeInternal(MakeExecutor executor) throws SlimException {
    return executor.create(instanceName, className, args);
  }

  public static interface MakeExecutor extends InstructionExecutor {
    Object create(String instanceName, String className, Object... constructorArgs) throws SlimException;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("{id='").append(getId()).append('\'');
    sb.append(", instruction='").append(INSTRUCTION).append('\'');
    sb.append(", instanceName='").append(instanceName).append('\'');
    sb.append(", className='").append(className).append('\'');
    sb.append(", args=").append(args == null ? "null" : Arrays.asList(args).toString());
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    MakeInstruction that = (MakeInstruction) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    if (!Arrays.equals(args, that.args)) return false;
    if (!className.equals(that.className)) return false;
    if (!instanceName.equals(that.instanceName)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + instanceName.hashCode();
    result = 31 * result + className.hashCode();
    result = 31 * result + Arrays.hashCode(args);
    return result;
  }
}