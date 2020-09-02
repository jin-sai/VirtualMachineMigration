import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class Greedy {

	public static List<Host> hostList[] = new ArrayList[5];
	public static List<Vm> vmList[] = new ArrayList[5];
	public static int biggesthost[]=new int[5];
	public static int hostsize[]=new int[5];
	public static int vmsize[]=new int[5];
	public static int numofdc;
	public static int no_of_vms_in_last_dc;
	public static Datacenter datacenter[]=new Datacenter[5];
	public static int brokerId;
	public static String reply;
	public static DatacenterBroker broker;

	public static void main(String[] args) {
		try {
			int num_user = 1; 
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;
			CloudSim.init(num_user, calendar, trace_flag);
			broker = createBroker();
			brokerId = broker.getId();
			Mainframe.main(null);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Unwanted errors happen");
		}
	}

	
	private static Datacenter createDatacenter(String name,List<Host>    host1) {

		List<Host> hostList = new ArrayList<Host>();
		hostList = host1; 

		String arch = "x86"; 
		String os = "Linux"; 
		String vmm = "Xen";
		double time_zone = 10.0; 
		double cost = 3.0; 
		double costPerMem = 0.05; 
		double costPerStorage = 0.001;					
		double costPerBw = 0.0; 
		LinkedList<Storage> storageList = new LinkedList<Storage>();

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(arch, os, vmm, hostList, time_zone, cost, costPerMem,costPerStorage, costPerBw);

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
			System.out.println(name+" is created");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}
	
	private static DatacenterBroker createBroker() {
		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	
	public static void SetNDC(int n){
		numofdc=n;
	}
	
	public static void SetNvm(int n){
		no_of_vms_in_last_dc=n;
	}
		
	public static void AddHost(int i,int j,int r,int s,int b){
		List<Pe> peList = new ArrayList<Pe>();
		int mips = 4000;
		int hostId = i*10+j;
		peList.add(new Pe(hostId, new PeProvisionerSimple(mips)));
		int ram = r;
		long storage =s; 
		int bw = b;

		Host h= new Host(
				hostId,
				new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw),
				storage,
				peList,
				new VmSchedulerTimeShared(peList)
			);
		hostsize[i]=hostsize[i]+ram;
		if(biggesthost[i]==0){
			biggesthost[i]=ram;
		}
		else{
			if(biggesthost[i]<ram){
				biggesthost[i]=ram;
			}
		}
		if(hostList[i]==null){
			hostList[i] = new ArrayList<Host>();
		}
		hostList[i].add(h);
		System.out.println("host "+hostId+" is added");
	}
	public static int AddVM(int i,int j,int r,int s,int b){
		int vmid = i*10+j;
		int mips = 1000;
		long size = s; 
		int ram = r; 
		long bw = b;
		int pesNumber = 1;
		String vmm = "Xen"; 
		vmsize[i]=vmsize[i]+ram;
		if(ram<=biggesthost[i]){
			if(hostsize[i]<vmsize[i]){
				vmsize[i]=vmsize[i]-ram;
				return 2;
			}
			else{
				if(vmList[i]==null){
					vmList[i]=new ArrayList<Vm>();
				}
				Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			    vmList[i].add(vm);
			    System.out.println("vm "+vmid+" is created");
			    if(i==numofdc&&j==no_of_vms_in_last_dc){
			    	for(int k=1;k<=numofdc;k++){
			    		datacenter[k]=createDatacenter("Datacenter_"+k,hostList[k]);
			    	}
			    	allocate_hosts_for_vms(1,hostList[1],vmList[1]);
			    	new Thread(new Runnable() {
			            public void run() {
			            	startProcess();             
			            }
			        }).start();
			    }
			    return 1;
			}
		}

		else{
			vmsize[i]=vmsize[i]-ram;
			return 0;
		}
	    
	}
	
	public static void allocate_hosts_for_vms(int k,List<Host> hostList1,List<Vm> vmlist1){
		List<Host> hostList= new ArrayList<Host>();
		List<Vm> vmList= new ArrayList<Vm>();
		hostList=hostList1;
		vmList=vmlist1;
		Collections.sort(hostList, new Comparator<Host>() {

			@Override
			public int compare(Host h1, Host h2) {
				return h2.getRam()-h1.getRam();
			}
		});
		
		Collections.sort(vmList, new Comparator<Vm>() {

			@Override
			public int compare(Vm vm1, Vm vm2) {
				return vm2.getRam()-vm1.getRam();
			}
		});
		int hostsize1 = hostList.size();
		for (int i = 0; i < hostsize1; i++){
			int vmsize1 = vmList.size();
			Host host=hostList.get(i);
			int ram=host.getRam();
			List<Integer> removedvms= new ArrayList<Integer>();
			for(int j=0;j<vmsize1;j++){
				Vm vm=vmList.get(j);
				if(vm.getHost()!=null){
					Host h2=vm.getHost();
					h2.vmDestroy(vm);
				}
				if(vm.getRam()<=ram){
					try{
						if(host.vmCreate(vm)){
							datacenter[k].getVmList().add(vm);
							removedvms.add(j);
							ram=ram-vm.getRam();
						}
					}
					catch(Exception e){
						System.out.println("VM " + vm.getId() + " cannot be allocated to the host " + host.getId());
					}
				}
			}
			for(int j=removedvms.size()-1;j>=0;j--){
				int k1=removedvms.get(j);
				vmList.remove(vmList.get(k1));
			}
			if(vmList.isEmpty()){
				break;
			}
		}
	}
	
	public static void startProcess(){
		reply =greedyAlgorithm(hostList[2], datacenter[1].getVmList());
		//reply =serialmethod(hostList[2], vmList[1]);
		if(reply==""){
			frame6 sixthframe=new frame6("no allocations are done");
			sixthframe.setVisible(true);
		}
		else{
			frame6 sixthframe=new frame6(reply);
			sixthframe.setVisible(true);
		}
	}

	public static String greedyAlgorithm(List<Host> hostList1,List<Vm> vmlist1){
		
		System.out.println("starting greedy method");
		String msg="";
		List<Host> hostlist= new ArrayList<Host>();
		List<Vm> vmlist= new ArrayList<Vm>();
		hostlist=hostList1;
		vmlist=vmlist1;
		Collections.sort(hostlist, new Comparator<Host>() {

			@Override
			public int compare(Host h1, Host h2) {
				return h2.getRam()-h1.getRam();
			}
		});
		
		Collections.sort(vmlist, new Comparator<Vm>() {

			@Override
			public int compare(Vm vm1, Vm vm2) {
				return vm2.getRam()-vm1.getRam();
			}
		});
		int hostsize1 = hostlist.size();
		for (int i = 0; i < hostsize1; i++){
			int vmsize1 = vmlist.size();
			Host host=hostlist.get(i);
			int ram=host.getRam();
			List<Integer> removedvms= new ArrayList<Integer>();
			for(int j=0;j<vmsize1;j++){
				Vm vm=vmlist.get(j);
				if(vm.getRam()<=ram){
					Host h2=vm.getHost();
					System.out.println("Deallocating VM " + vm.getId() + " from the host " + h2.getId());
					h2.vmDestroy(vm);
					System.out.println("allocating VM " + vm.getId() + " to the host " + host.getId());
					try{
						if(host.vmCreate(vm)){
							msg = msg + "<br>VM " + vm.getId() + " has been migrated to the host " + host.getId()+"</br>";
							System.out.println("VM " + vm.getId() + " is allocated to the host " + host.getId());
							removedvms.add(j);
							ram=ram-vm.getRam();
						}
					}
					catch(Exception e){
						System.out.println("VM " + vm.getId() + " cannot be migrated to the host " + host.getId());
					}
				}
			}
			for(int j=removedvms.size()-1;j>=0;j--){
				int k=removedvms.get(j);
				vmlist.remove(vmlist.get(k));
			}
			if(vmlist.isEmpty()){
				break;
			}
		}
		return msg;
	}
	
	
public static String serialmethod(List<Host> hostList1,List<Vm> vmlist1){
		
		System.out.println("starting serial method");
		String msg="";
		List<Host> hostList= new ArrayList<Host>();
		List<Vm> vmList= new ArrayList<Vm>();
		hostList=hostList1;
		vmList=vmlist1;
		int hostsize1 = hostList.size();
		for (int i = 0; i < hostsize1; i++){
			int vmsize1 = vmList.size();
			Host host=hostList.get(i);
			int ram=host.getRam();
			List<Integer> removedvms= new ArrayList<Integer>();
			for(int j=0;j<vmsize1;j++){
				Vm vm=vmList.get(j);
				if(vm.getRam()<=ram){
					Host h2=vm.getHost();
					System.out.println("Deallocating VM " + vm.getId() + " from the host " + h2.getId());
					h2.vmDestroy(vm);
					System.out.println("allocating VM " + vm.getId() + " to the host " + host.getId());
					try{
						if(host.vmCreate(vm)){
							msg = msg + "<br>VM " + vm.getId() + " has been migrated to the host " + host.getId()+"</br>";
							System.out.println("VM " + vm.getId() + " is allocated to the host " + host.getId());
							removedvms.add(j);
							ram=ram-vm.getRam();
						}
					}
					catch(Exception e){
						System.out.println("VM " + vm.getId() + " cannot be migrated to the host " + host.getId());
					}
				}
			}
			for(int j=removedvms.size()-1;j>=0;j--){
				int k=removedvms.get(j);
				vmList.remove(vmList.get(k));
			}
			if(vmList.isEmpty()){
				break;
			}
		}
		return msg;
	}
	
	
}
