package fr.hetic.app_map_amis.data

class ContactList {

    companion object{

        fun createDataSet(): ArrayList<Contact>{
            val list = ArrayList<Contact>()
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Capucine",
                    "capugc@hotmail.fr"

                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Martin",
                    "martin.prejean@hetic.net"
                )
            )

            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Louis",
                    "0617945724"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Diego",
                    "0634437187"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Martine",
                    "martine@gmail.fr"
                )
            )
            list.add(
                Contact(
                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    "Quentin",
                    "0748559864"
                )
            )

            return list
        }
    }
}